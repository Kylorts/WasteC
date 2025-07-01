from pydantic import BaseModel
from typing import List

class WasteCategory(BaseModel):
    id: int
    name: str
    description: str
    icon_url: str
    examples: List[str]
    recycling_info: str