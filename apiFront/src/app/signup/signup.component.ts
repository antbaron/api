import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  registerForm: FormGroup;
    loading = false;
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private userService: UserService
    ) {
    }

    checkPasswords(group: FormGroup) { // here we have the 'passwords' group
        if(group && group.controls && group.controls.password.value && group.controls.passwordConfirmation.value){
            let pass = group.controls.password.value;
            let confirmPass = group.controls.passwordConfirmation.value;

            return pass === confirmPass ? null : { notSame: true };
        }
        return null;    
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(6)]],
            passwordConfirmation: ['', [Validators.required, Validators.minLength(6)]]
        }, {validator: this.checkPasswords });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;
        
        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }
        this.loading = true;

        this.userService.register(this.f.username.value, this.f.password.value).subscribe(
            (res)=>{
                this.loading = false;
                this.router.navigate(['/']);
            },
            (error)=>{
                this.loading = false;
            }
        );       
        
        
    }

}
