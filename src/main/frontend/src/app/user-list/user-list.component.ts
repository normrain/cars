import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterLink, RouterLinkActive} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import {User} from "../user.model";
import {MatTabsModule} from "@angular/material/tabs";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Car} from "../car.model";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatTableModule, MatIconModule, HttpClientModule, MatTabsModule, RouterLinkActive, MatCardModule, MatListModule],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent {
  title = 'User List';
  loading = true;
  users: User[] = [];
  displayedColumns = ['id','name', 'cars'];
  feedback: any = {};

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loading = true;
    this.http.get<User[]>("http://localhost:8080/api/v1/users").subscribe((data: User[]) => {
      this.users = data;
      this.loading = false;
      this.feedback = {};
    });
  }
}
