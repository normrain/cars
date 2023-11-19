import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {MatTabsModule} from "@angular/material/tabs";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatRadioModule} from "@angular/material/radio";
import {MatSelectModule} from "@angular/material/select";
import {User} from "../user.model";
import {Car} from "../car.model";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {MatListModule} from "@angular/material/list";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule,
    MatButtonModule,
    MatTabsModule,
    RouterLink,
    RouterLinkActive,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    HttpClientModule, MatListModule, MatTableModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  constructor(private formBuilder:FormBuilder, private http: HttpClient) {
  }
  objectToSearch = 'users';

  userSearchResult: User[] = [];
  carSearchResult: Car[] =[];
  displayedColumns: string[] = []

  searchForm = this.formBuilder.group({
    searchObject: [''],
    searchTerm: [''],
    sortValue: [''],
    sortDirection: ['']
  })
  userOptions: any = [
    {value: 'id', viewValue: 'Id'},
    {value: 'name', viewValue: 'Name'},
  ];
  carOptions: any = [
    {value: 'id', viewValue: 'Id'},
    {value: 'make', viewValue: 'Make'},
    {value: 'model', viewValue: 'Model'},
    {value: 'numberplate', viewValue: 'Number Plate'},
  ];
  sendForm(){
    this.resetSearchResults();
    let searchUrl = "http://localhost:8080/api/v1/" +
      this.searchForm.value.searchObject +
      "?find=" +
      this.searchForm.value.searchTerm +
      "&sort=" +
      this.searchForm.value.sortValue + ":" + this.searchForm.value.sortDirection
    if(this.searchForm.value.searchObject == "cars"){
      this.http.get<Car[]>(searchUrl).subscribe((data: Car[]) => {
        this.carSearchResult = data;
        console.table(data)
      });
      this.displayedColumns = ['id','make','model', 'numberplate'];
    }
    if(this.searchForm.value.searchObject == "users"){
      this.http.get<User[]>(searchUrl).subscribe((data: User[]) => {
        this.userSearchResult = data;
        console.table(data)
      });
      this.displayedColumns = ['id','name', 'cars'];
    }

  }

  resetSearchResults() {
    this.carSearchResult = [];
    this.userSearchResult = [];
  }

}
