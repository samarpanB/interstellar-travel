import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NbAuthModule } from "@nebular/auth";
import { NbEvaIconsModule } from "@nebular/eva-icons";
import {
  NbButtonModule,
  NbCardModule,
  NbLayoutModule,
  NbMenuModule,
  NbSelectModule,
  NbSidebarModule,
  NbThemeModule
} from "@nebular/theme";
import { Ng2SmartTableModule } from "ng2-smart-table";
import { ToastrModule } from "ngx-toastr";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { AuthConfig } from "./auth-config";
import { CoreModule } from "./core/core.module";
import { MainComponent } from "./main/main.component";
import { TravelComponent } from "./travel/travel.component";
import { UploadComponent } from "./upload/upload.component";

@NgModule({
  declarations: [AppComponent, MainComponent, UploadComponent, TravelComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CoreModule,
    FormsModule,
    NbThemeModule.forRoot(),
    NbAuthModule.forRoot(AuthConfig),
    NbEvaIconsModule,
    HttpClientModule,
    NbLayoutModule,
    NbSidebarModule.forRoot(),
    NbMenuModule,
    NbButtonModule,
    NbCardModule,
    NbSelectModule,
    Ng2SmartTableModule,
    ToastrModule.forRoot({
      preventDuplicates: true
    })
  ],
  providers: [
    ...NbSidebarModule.forRoot().providers,
    ...NbMenuModule.forRoot().providers
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
