import { Component, OnInit } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-travel",
  templateUrl: "./travel.component.html",
  styleUrls: ["./travel.component.scss"]
})
export class TravelComponent implements OnInit {
  constructor(
    private readonly http: HttpClient,
    private readonly toastrService: ToastrService
  ) {}

  planets: any[];
  data = [];
  selectedSource: string = null;
  selectedDest: string = null;
  tableSettings = {
    columns: {
      source: {
        title: "Source",
        editable: false,
        sort: false,
        filter: false
      },
      destination: {
        title: "Destination",
        editable: false,
        sort: false,
        filter: false
      },
      distance: {
        title: "Distance",
        editable: false,
        sort: false,
        filter: false
      }
    },
    actions: null
  };

  ngOnInit() {
    this.getPlanets();
  }

  getPlanets() {
    this.http.get(`${environment.baseUrl}/planets`).subscribe(resp => {
      this.planets = resp as any[];
    });
  }

  findPath() {
    this.data = [];
    if (
      this.selectedSource &&
      this.selectedDest &&
      this.selectedDest !== this.selectedSource
    ) {
      this.http
        .get(`${environment.baseUrl}/shortest-path`, {
          params: new HttpParams()
            .set("src", this.selectedSource)
            .set("dest", this.selectedDest)
        })
        .subscribe(
          resp => {
            this.data = (resp as any[]).map(d => {
              const retObj = Object.assign({}, d);
              retObj.source = d.source.name;
              retObj.destination = d.destination.name;
              return retObj;
            });
          },
          () => {
            this.toastrService.error("No path found", "ERROR !", {
              timeOut: 4000
            });
          }
        );
    } else {
      this.toastrService.error("Invalid source or destination", "ERROR !", {
        timeOut: 4000
      });
    }
  }
}
