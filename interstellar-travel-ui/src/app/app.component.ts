import { Component, Inject, OnInit } from "@angular/core";
import { NbAuthService } from "@nebular/auth";
import { StorageService } from "angular-webstorage-service";

import { APP_SESSION_STORE } from "./core/store/store.interface";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent implements OnInit {
  title = "interstellar-travel-ui";

  constructor(
    private readonly authService: NbAuthService,
    @Inject(APP_SESSION_STORE) private readonly store: StorageService
  ) {}

  ngOnInit() {
    this.authService.onAuthenticationChange().subscribe(res => {
      this.store.set("logged_in", res);
    });
  }
}
