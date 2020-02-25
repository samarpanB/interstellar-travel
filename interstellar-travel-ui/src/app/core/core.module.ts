import { CommonModule } from "@angular/common";
import { NgModule, Optional, SkipSelf } from "@angular/core";
import {
  InMemoryStorageService,
  SESSION_STORAGE,
  StorageServiceModule,
  LOCAL_STORAGE
} from "angular-webstorage-service";

import { APP_SESSION_STORE, APPLICATION_STORE } from "./store/store.interface";
import { EnsureModuleLoadedOnce } from "./ensure-module-loaded-once";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthInterceptor } from "./auth/auth.interceptor";

@NgModule({
  declarations: [],
  imports: [CommonModule, StorageServiceModule],
  providers: [
    { provide: APPLICATION_STORE, useExisting: LOCAL_STORAGE },
    { provide: APP_SESSION_STORE, useExisting: SESSION_STORAGE },
    InMemoryStorageService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ]
})
export class CoreModule extends EnsureModuleLoadedOnce {
  // Ensure that CoreModule is only loaded into AppModule

  // Looks for the module in the parent injector to see if it's already been loaded (only want it loaded once)
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    super(parentModule);
  }
}
