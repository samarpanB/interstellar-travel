import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { NbAuthComponent, NbLoginComponent } from "@nebular/auth";
import { environment } from "src/environments/environment";
import { MainComponent } from "./main/main.component";
import { LoggedInGuard } from "./core/auth/logged-in.guard";
import { AuthGuard } from "./core/auth/auth.guard";
import { UploadComponent } from "./upload/upload.component";
import { TravelComponent } from "./travel/travel.component";

const routes: Routes = [
  {
    path: "auth",
    component: NbAuthComponent,
    children: [
      {
        path: "",
        component: NbLoginComponent
      },
      {
        path: "login",
        component: NbLoginComponent
      }
    ],
    canActivate: [LoggedInGuard],
    canActivateChild: [LoggedInGuard]
  },
  {
    path: "main",
    component: MainComponent,
    children: [
      {
        path: "upload",
        component: UploadComponent
      },
      {
        path: "travel",
        component: TravelComponent
      }
    ],
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard]
  },
  {
    path: "",
    redirectTo: environment.homePath,
    pathMatch: "full"
  },
  {
    path: "**",
    redirectTo: environment.homePath
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
