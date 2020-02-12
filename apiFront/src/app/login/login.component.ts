import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';
import { User } from '../user';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  ngOnDestroy(): void {
    if(this.observable){
      this.observable.unsubscribe();
    }
  }

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error: boolean;
  observable: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.error = false;
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.observable = this.userService.login(this.f.username.value, this.f.password.value).subscribe(
      (res)=> {
        if(res.ok){
          this.userService.userId = this.f.username.value;
          this.router.navigate(['/protected']);
        }else{
          this.error = true;
          this.loading = false;
        }
      },
      ()=> {
        this.error = true;
        this.loading = false;
      }
    );    
  }

}
