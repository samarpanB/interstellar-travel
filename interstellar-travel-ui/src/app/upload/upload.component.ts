import { Component, OnInit } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpClient } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";

@Component({
  selector: "app-upload",
  templateUrl: "./upload.component.html",
  styleUrls: ["./upload.component.scss"]
})
export class UploadComponent {
  constructor(
    private readonly http: HttpClient,
    private readonly toastrService: ToastrService,
    private readonly router: Router
  ) {}

  onFileSelect(fileInput) {
    const file = fileInput.target.files[0] as File;
    const formData = new FormData();
    formData.append("file", file);

    this.http
      .post(`${environment.baseUrl}/bulk-upload-excel`, formData)
      .subscribe(
        () => {
          this.router.navigate(["/main/travel"]);
        },
        () => {
          this.toastrService.error(
            "Error occured while uploading. Please try again.",
            "ERROR !",
            {
              timeOut: 4000
            }
          );
        }
      );
  }
}
