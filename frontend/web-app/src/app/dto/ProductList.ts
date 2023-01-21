import {Product} from "./Product";

export interface ProductList{

   id : bigint
   name : string
   products : Array<Product>
   description : string
   dueTo : Date;
}
