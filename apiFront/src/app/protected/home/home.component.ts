import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { Observable } from 'rxjs';
import { User } from 'src/app/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userId: String;
  constructor(private userService:UserService, private router: Router) { }

  ngOnInit() {
    this.userId = this.userService.userId;
    if(!this.userId){
      this.router.navigate(['/']);
    }
  }
    
}
