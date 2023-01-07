import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";

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

  public generateData(): void {
     this.http.get<String>(`${this.apiServerUrl}/api/maxsat/GenerateData`).subscribe(
       response => { console.log(response)}
     );
  }

  public assignCity(city: String): void {
    this.http.get<String>(`${this.apiServerUrl}/api/maxsat/AssignCity?city=${city}`).subscribe(
      response => { console.log(response)}
    );
  }

  public solve(cordX: Number, cordY: Number,usersChoices: String []): void {
    this.http.get<String>(`${this.apiServerUrl}/api/maxsat/sfps?CordX=${cordX}&CordY=${cordY}&usersChoices=${usersChoices}`, this.requestOptions).subscribe(
      response => { console.log(response) ; this.response = String(response)}
    );
  }
}
