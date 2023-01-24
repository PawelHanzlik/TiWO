import {Injectable} from "@angular/core";
import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree,} from "@angular/router";
import {Observable} from "rxjs";
import {AppService} from "./app-service";


@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  constructor(public appService: AppService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log("guard " + localStorage.getItem("token"))
    if (localStorage.getItem("token") != null && localStorage.getItem("token") != ""){
      return true;
    }
    else{
      this.router.navigate(['/login']);
    }
    return false;
  }
}
