import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from "@angular/material/table";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Car} from "../car.model";
import {MatTabsModule} from "@angular/material/tabs";

@Component({
  selector: 'app-car-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, RouterLink, HttpClientModule, MatTabsModule, RouterLinkActive],
  templateUrl: './car-list.component.html',
  styleUrl: './car-list.component.css'
})
export class CarListComponent {
  title = 'Car List';
  loading = true;
  cars: Car[] = [];
  displayedColumns = ['id','make','model', 'numberplate'];

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loading = true;
    this.http.get<Car[]>("http://localhost:8080/api/v1/cars").subscribe((data: Car[]) => {
      this.cars = data;
      this.loading = false;
    });
  }

}
