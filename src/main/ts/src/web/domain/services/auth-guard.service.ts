import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    CanActivateChild,
    Router,
    RouterStateSnapshot
} from '@angular/router';

import { Http } from '@angular/http';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    /**
     *
     */
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {}

    /**
     * 
     * @param route 
     * @param state 
     */
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let activate = true;

        this.authenticationService.getAuthenticatedUser()
        .then((res) => {
            localStorage.setItem('curUser', res.nome);
            //this.router.navigate(route.data.name);
        })
        .catch(() => {
            this.router.navigate(['login']);
            localStorage.removeItem('curUser');
            activate = false;
        });

        let curUser = localStorage.getItem('curUser') || "";
        activate = (curUser || curUser.length) ? true : false;

        return activate;
    }

    /**
     * 
     * @param route 
     * @param state 
     */
    canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.canActivate(route, state);
    }
}
