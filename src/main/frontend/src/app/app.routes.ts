import { Routes } from '@angular/router';
import {SearchComponent} from "./search/search.component";
import {UserListComponent} from "./user-list/user-list.component";
import {Car} from "./car.model";
import {CarListComponent} from "./car-list/car-list.component";

export const routes: Routes = [
  { path: '', redirectTo: '/search', pathMatch: 'full' },
  {
    path: 'search',
    component: SearchComponent
  },
  {
    path: 'users',
    component: UserListComponent
  },
  {
    path: 'cars',
    component: CarListComponent
  }
];
