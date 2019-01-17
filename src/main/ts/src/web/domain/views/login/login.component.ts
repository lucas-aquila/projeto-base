import { Component } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { MatDialog } from '@angular/material';

import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { WildcardService } from '../../services/wildcard.service';

/**
 *
 */
@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    /**
     *
     */
    public usuario: any = {};

    /**
     * 
     * @param router 
     * @param authenticationService 
     */
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {}

    /**
     * 
     */
    public login() {
        this.authenticationService.login(this.usuario)
        .then((result) => {
            console.log(result);
            localStorage.setItem('curUser', result.nome);
            this.authenticationService.getAuthenticatedUser()
            .then(authenticated => {
                this.usuario = authenticated;
                this.authenticationService.setAuthenticatedUser(this.usuario);

                this.router.navigate(['/']);
            });
        });
    }
}
