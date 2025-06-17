
# Timestamp: timestamp
# measurement: MessageType
# tags: counter, Source, Destination
# fields: TraceID and all other columns as key-value pairs using FieldX and ValueX

import pandas as pd
from pathlib import Path

csv_path = Path(r"C:\Program Files\GrafanaLabs\tempo_proj\data")
csv_file = "radarTraceVer3.csv"
data_folder = csv_path / csv_file

df = pd.read_csv(data_folder)
lines = []

def process_row(row):
    measurement = row['MessageType']
    tags = f"counter={row['counter']},Source={row['Source']},Destination={row['Destination']}"

    fields = []

    # Include TraceID explicitly as a field
    if 'TraceID' in row and pd.notna(row['TraceID']):
        trace_id = row['TraceID']
        trace_id = f'"{trace_id}"' if isinstance(trace_id, str) else trace_id
        fields.append(f"TraceID={trace_id}")

    for i in range(1, 10):  # Assuming up to Field9/Value9
        field_col = f"Field{i}"
        value_col = f"Value{i}"
        if field_col in row and value_col in row and pd.notna(row[field_col]) and pd.notna(row[value_col]):
            key = str(row[field_col]).replace(" ", "_")
            value = row[value_col]
            if isinstance(value, str) and not value.replace('.', '', 1).isdigit():
                value = f'"{value}"'
            fields.append(f"{key}={value}")

    if fields:
        timestamp = pd.to_datetime(row['Timestamp']).value  # Convert to nanoseconds
        line = f"{measurement},{tags} {','.join(fields)} {timestamp}"
        lines.append(line)

df.apply(process_row, axis=1)
print(lines)
with open("dataVer2.txt", "w") as f:
    f.write("\n".join(lines))

print("InfluxDB line protocol data saved to dataVer2.txt")
