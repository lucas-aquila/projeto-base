import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

const PATH_AUTHENTICATE = 'authenticate';
const PATH_LOGOUT = 'logout';
const PATH_AUTHENTICATED = 'authenticated';

@Injectable()
export class AuthenticationService {
    public authenticatedUser: any = null;
    public authenticatedUserChanged: EventEmitter<any>;

    /**
     *
     * @param http
     */
    constructor(private http: HttpClient, private router: Router) {
        this.authenticatedUserChanged = new EventEmitter();

        // Pega o usuário logado
        //this.getAuthenticatedUser().then(result => this.authenticatedUser = result);
    }

    /**
     *
     */
    public getAuthenticatedUser(): Promise<any> {

    return this.http.get(PATH_AUTHENTICATED)
        .toPromise()
        //.catch(() => this.router.navigate(['login']));
    }

    /**
     * 
     * @param authenticatedUser 
     */
    public setAuthenticatedUser(authenticatedUser: any) {
        this.authenticatedUser = authenticatedUser;
        this.authenticatedUserChanged.emit(this.getAuthenticatedUser());
    }

    /**
     *
     * @param usuario
     */
    public login(usuario: any): Promise<any> {
        let body = new HttpParams();
        body = body.set('email', usuario.email || '');
        body = body.set('senha', usuario.senha || '');

        return this.http.post<any>(PATH_AUTHENTICATE, body).toPromise();
    }

    /**
     *
     */
    public logout(): Promise<any> {
        return this.http.get(PATH_LOGOUT).toPromise();
    }

    /**
     * 
     * @param codigo 
     * @param senha 
     */
    public resetSenha(codigo: any, senha: any): Promise<any> {
        let params = new HttpParams();
        params = params.set('senha', senha);

        return this.http.post(`/cadastrar-senha/${codigo}`, params).toPromise();
    }

    /**
     * 
     * @param email 
     */
    public recoverSenha(email: any): Promise<any> {
        return this.http.get(`/recuperar-senha/${email}`).toPromise();
    }
}
