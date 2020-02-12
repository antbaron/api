import { Injectable } from '@angular/core';
import { Observable, of, Subject, BehaviorSubject } from 'rxjs';
import { User } from './user';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userId: string;
  
  constructor(private http$: HttpClient) {}

  login(name, password): Observable<ResponseOk>{
    return this.http$.post<ResponseOk>(`http://localhost:8080/users/${name}/login`, { password });
  }

  register(name, password): Observable<ResponseOk>{
    return this.http$.post<ResponseOk>(`http://localhost:8080/users/${name}`, { password });
  }
  
}

interface ResponseOk {
  ok: boolean;
}