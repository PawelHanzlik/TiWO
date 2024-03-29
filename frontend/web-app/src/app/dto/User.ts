import {ProductList} from "./ProductList";

export interface User {
  id : bigint
  name : string
  surname : string
  email : string
  password : string
  money : bigint
  productLists : Set<ProductList>
}
