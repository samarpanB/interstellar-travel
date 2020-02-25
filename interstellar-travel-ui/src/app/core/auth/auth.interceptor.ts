import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { StorageService } from "angular-webstorage-service";
import { Observable, throwError } from "rxjs";

import { APPLICATION_STORE } from "../store/store.interface";

export const AuthTokenSkipHeader = "X-Skip-Auth-Token";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    @Inject(APPLICATION_STORE) private readonly store: StorageService,
    private readonly router: Router
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (req.headers.has(AuthTokenSkipHeader) || req.url.includes("/login")) {
      const headers = req.headers.delete(AuthTokenSkipHeader);
      return next.handle(req.clone({ headers }));
    }
    const authTokenData = this.store.get("auth_app_token");

    if (authTokenData) {
      const authToken = authTokenData.value;
      return next.handle(
        req.clone({ setHeaders: { Authorization: `Basic ${authToken}` } })
      );
    } else {
      if (this.router.url !== "/" && this.router.url !== "/login") {
        this.router.navigate(["/"]);
      }
      return throwError("Request forbidden ! No access token available.");
    }
  }
}
