import { Component, OnInit } from "@angular/core";
import { NbMenuItem } from "@nebular/theme";

@Component({
  selector: "app-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"]
})
export class MainComponent implements OnInit {
  constructor() {}

  menuItems: NbMenuItem[] = [
    {
      title: "Upload",
      link: "/main/home",
      home: true
    },
    {
      title: "Travel",
      link: "/main/travel",
      home: false
    }
  ];

  ngOnInit() {}
}
