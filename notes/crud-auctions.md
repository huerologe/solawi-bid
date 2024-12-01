# CRUD Auctions

Creating and reading auctions is already possible.
So we concentrate on updates and deletion here.
 
Todos:
- [x] Add put, patch, delete functions to requests
- [ ] Delete auctions:
  - [x] Add serializable data classes under ``` solawi-bid-api-data/solyton/solawi/bid(module|application)/.../data ```
  - [ ] Add Endpoint to Api under ```solawi-bid-api-data//solyton/solawi/bid/api/solawi-api ```
  - [x] Add serializers for ApiTypes
      - [x] Frontend
      - [x] Backend
  - [x] If necessary, add transform functions to
      - Backend
          - [x] ```EntityType -> ApiType ```
          - [x] ```ApiType -> EntityType  ```
      - Frontend
          - [x] ```LensType -> ApiType```
          - [x] ```ApiType -> LensType```
  - [ ] Add corresponding Action to frontend
  - [ ] Implement endpoint