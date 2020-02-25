import { Injectable, Inject } from "@angular/core";
import { CanActivate, Router, CanActivateChild } from "@angular/router";
import { APP_SESSION_STORE } from "../store/store.interface";
import { StorageService } from "angular-webstorage-service";
import { map } from "rxjs/operators";
import { Observable, of } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root"
})
export class LoggedInGuard implements CanActivate, CanActivateChild {
  constructor(
    private readonly router: Router,
    @Inject(APP_SESSION_STORE) private readonly store: StorageService
  ) {}

  canActivate(): Observable<boolean> {
    return of(this.store.get("logged_in")).pipe(
      map(res => {
        if (res) {
          this.router.navigate([environment.homePath]);
        }
        return !res;
      })
    );
  }

  canActivateChild(): Observable<boolean> {
    return of(this.store.get("logged_in")).pipe(
      map(res => {
        if (res) {
          this.router.navigate([environment.homePath]);
        }
        return !res;
      })
    );
  }
}
