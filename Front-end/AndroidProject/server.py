from fastapi import FastAPI
from typing import Optional, List

app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Optional[str] = None):
    return {"item_id": item_id, "q": q}

@app.get("/items")
def read_items(q: Optional[str] = None):
    items = [
        {"item_id": 1, "name": "Item 1", "description": "This is item 1"},
        {"item_id": 2, "name": "Item 2", "description": "This is item 2"},
        {"item_id": 3, "name": "Item 3", "description": "This is item 3"},
        {"item_id": 4, "name": "Item 4", "description": "This is item 4"},
        {"item_id": 5, "name": "Item 5", "description": "This is item 5"},
        {"item_id": 6, "name": "Item 6", "description": "This is item 6"}
    ]
    if q:
        items = [item for item in items if q.lower() in item["name"].lower()]
    return {"items": items}
