#!/usr/bin/env python3
"""Build a privacy-reduced 28-day summary from a local Mi Fitness export.

The source export stays local. This script reads only daily_report rows and
writes weekly means with sequential week numbers; it drops account/device IDs
and calendar timestamps from the public output.
"""

from __future__ import annotations

import csv
import json
from collections import defaultdict
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
DATA_DIR = ROOT / "data"
OUTPUT = DATA_DIR / "public_weekly_summary.csv"

METRICS = {
    "steps": ("steps", "mean_daily_steps"),
    "calories": ("calories", "mean_daily_calories_field"),
    "heart_rate": ("avg_hr", "mean_daily_avg_hr"),
}


def find_export() -> Path:
    matches = list(DATA_DIR.rglob("*_MiFitness_hlth_center_aggregated_fitness_data.csv"))
    if len(matches) != 1:
        raise SystemExit(
            "Expected exactly one local Mi Fitness aggregated export CSV under data/. "
            "Keep the original export local; it is ignored by Git."
        )
    return matches[0]


def numeric(value: object) -> float | None:
    if isinstance(value, bool) or not isinstance(value, (int, float)):
        return None
    return float(value)


def main() -> None:
    source = find_export()

    # Keep the latest daily_report value for each UTC day and data type.
    daily: dict[tuple[int, str], tuple[int, float]] = {}
    with source.open("r", encoding="utf-8-sig", newline="") as handle:
        for row in csv.DictReader(handle):
            if row.get("Tag") != "daily_report":
                continue
            key = row.get("Key", "")
            if key not in METRICS:
                continue
            try:
                timestamp = int(row.get("Time", ""))
                updated = int(row.get("UpdateTime", "0") or 0)
                payload = json.loads(row.get("Value", "{}"))
            except (TypeError, ValueError, json.JSONDecodeError):
                continue
            if not isinstance(payload, dict):
                continue

            field, _ = METRICS[key]
            value = numeric(payload.get(field))
            if value is None:
                continue
            day = timestamp // 86400
            row_key = (day, key)
            previous = daily.get(row_key)
            if previous is None or updated >= previous[0]:
                daily[row_key] = (updated, value)

    if not daily:
        raise SystemExit("No usable daily_report rows found in the local export.")

    first_day = min(day for day, _ in daily)
    periods: dict[int, dict[str, list[float]]] = defaultdict(lambda: defaultdict(list))
    for (day, key), (_, value) in daily.items():
        period = (day - first_day) // 28 + 1
        periods[period][key].append(value)

    fields = [
        "period_index",
        "days_with_steps",
        "mean_daily_steps",
        "days_with_calories",
        "mean_daily_calories_field",
        "days_with_heart_rate",
        "mean_daily_avg_hr",
    ]
    DATA_DIR.mkdir(parents=True, exist_ok=True)
    with OUTPUT.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fields, lineterminator="\n")
        writer.writeheader()
        for period in sorted(periods):
            metrics = periods[period]
            row: dict[str, int | str] = {"period_index": period}
            for key, (_, output_name) in METRICS.items():
                values = metrics.get(key, [])
                count_name = {
                    "steps": "days_with_steps",
                    "calories": "days_with_calories",
                    "heart_rate": "days_with_heart_rate",
                }[key]
                row[count_name] = len(values)
                if values:
                    mean = sum(values) / len(values)
                    step = {"steps": 100, "calories": 50, "heart_rate": 5}[key]
                    row[output_name] = int(round(mean / step) * step)
                else:
                    row[output_name] = ""
            writer.writerow(row)

    print(f"Wrote privacy-reduced 28-day summary: {OUTPUT.relative_to(ROOT)}")
    print(f"Periods: {len(periods)}; source rows and identity fields were not copied.")


if __name__ == "__main__":
    main()
