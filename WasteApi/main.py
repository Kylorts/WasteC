from fastapi import FastAPI, HTTPException
from typing import List 
from model.waste_categories import WasteCategory
from data.waste_categories import WasteCategories
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()


@app.get("/")
def read_root():
    return {"Welcome to WasteApi"}

@app.get("/waste-categories", response_model=List[WasteCategory])
def get_all_waste_categories():
    return WasteCategories

@app.get("/waste-categories/{category_id}", response_model=WasteCategory)
def get_waste_category(category_id: int):
    for waste_category in WasteCategories:
        if waste_category.id == category_id:
            return waste_category
    raise HTTPException(status_code=404, detail="Waste category not found")

app.add_middleware(
    CORSMiddleware,
    # isi dengan IP lokal Anda di laptop/pc
    allow_origins=["http://192.168.180.7:8000"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)