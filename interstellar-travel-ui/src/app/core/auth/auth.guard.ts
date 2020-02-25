import { Inject, Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  Router,
  RouterStateSnapshot
} from "@angular/router";
import { StorageService } from "angular-webstorage-service";
import { Observable, of } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";

import { APP_SESSION_STORE } from "../store/store.interface";

@Injectable({
  providedIn: "root"
})
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad {
  constructor(
    private readonly router: Router,
    @Inject(APP_SESSION_STORE) private readonly store: StorageService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this.checkLogin(state.url);
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this.checkLogin(state.url);
  }

  canLoad(route: Route): Observable<boolean> {
    return this.checkLogin(`/${route.path}`);
  }

  private checkLogin(url: string): Observable<boolean> {
    return of(this.store.get("logged_in")).pipe(
      map(result => {
        if (!result) {
          this.router.navigate([environment.defaultPath]);
        }
        return result;
      })
    );
  }
}
