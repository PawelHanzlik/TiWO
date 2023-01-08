import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private apiServerUrl = environment.apiBaseUrl;
  requestOptions: Object = {
    responseType: 'text'
  }
  response = ""
  constructor(private http: HttpClient) { }

  public login(email: String, password: String): Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/tiwo/user/login?email=${email}&password=${password}`)
  }

  public register(user: user, password: String): void {
    this.http.post<Map<string, string>>(`${this.apiServerUrl}/tiwo/user/register?password=${password}`,{
      "name" : user.name,
      "surname" : user.surname,
      "email" : user.email,
      "productLists" : []
    }).subscribe(
      response => { console.log(response)}
    );
  }

  public solve(cordX: Number, cordY: Number,usersChoices: String []): void {
    this.http.get<String>(`${this.apiServerUrl}/api/maxsat/sfps?CordX=${cordX}&CordY=${cordY}&usersChoices=${usersChoices}`, this.requestOptions).subscribe(
      response => { console.log(response) ; this.response = String(response)}
    );
  }
}
interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
