import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../types/login-response.type';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  // URL dinâmica que funciona tanto em desenvolvimento quanto em produção com Docker
  private apiUrl: string;

  constructor(private httpClient: HttpClient) {
    // Determina a URL base baseada no ambiente
    this.apiUrl = this.getApiUrl();
  }

  private getApiUrl(): string {
    // Se estiver rodando no navegador (desenvolvimento ou produção)
    if (typeof window !== 'undefined') {
      const hostname = window.location.hostname;
      const port = window.location.port;
      
      // Desenvolvimento local
      if (hostname === 'localhost' && port === '4200') {
        return 'http://localhost:8080/auth';
      }
      
      // Produção com Docker (front-end na porta 80, back-end na 8080)
      if (hostname === 'localhost' && port === '80') {
        return 'http://localhost:8080/auth';
      }
      
      // Quando front-end e back-end estão no mesmo host no Docker
      // Usa caminho relativo com proxy nginx
      return '/api/auth';
    }
    
    // Fallback para desenvolvimento
    return 'http://localhost:8080/auth';
  }

  login(email: string, password: string) {
    return this.httpClient
      .post<LoginResponse>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((value) => {
          sessionStorage.setItem('auth-token', value.token);
          sessionStorage.setItem('username', value.name);
        })
      );
  }

  signup(name: string, email: string, password: string) {
    return this.httpClient
      .post<LoginResponse>(`${this.apiUrl}/register`, { name, email, password })
      .pipe(
        tap((value) => {
          sessionStorage.setItem('auth-token', value.token);
          sessionStorage.setItem('username', value.name);
        })
      );
  }

  // Método auxiliar para verificar se usuário está autenticado
  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('auth-token');
  }

  // Método para logout
  logout(): void {
    sessionStorage.removeItem('auth-token');
    sessionStorage.removeItem('username');
  }

  // Método para obter token
  getToken(): string | null {
    return sessionStorage.getItem('auth-token');
  }

  // Método para obter nome do usuário
  getUsername(): string | null {
    return sessionStorage.getItem('username');
  }
}