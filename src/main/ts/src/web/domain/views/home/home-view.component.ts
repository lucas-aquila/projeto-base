import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoadingMode, LoadingType, TdLoadingService } from '@covalent/core';
import {
    RouteConfigLoadEnd,
    RouteConfigLoadStart,
    Router
} from '@angular/router';
import { MatDialog } from '@angular/material';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../../domain/services/authentication.service';
import { AlterarSenhaDialogComponent } from './cadastros/usuario/alterar-senha-dialog.component';
import { MessageService } from '../../services/message.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'home-view',
    templateUrl: './home-view.component.html',
    styleUrls: ['./home-view.component.scss']
})
export class HomeViewComponent implements OnInit, OnDestroy {
    /**
     * 
     */
    public usuario: any;
    public routerSubscription: Subscription;
    public userSubscription: Subscription;

    /**
     * 
     */
    public toolbar: any = { headline: 'Cadastros', subhead: '' };

    /**
     * 
     * @param authenticationService 
     * @param router 
     * @param loadingService 
     */
    constructor(
        public authenticationService: AuthenticationService,
        public router: Router,
        public loadingService: TdLoadingService,
        public dialog: MatDialog,
        public messageService: MessageService,
        public translate: TranslateService
    ) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('pt-br');

        // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('pt-br');


        this.loadingService.create({
            name: 'loadingLogin',
            mode: LoadingMode.Indeterminate,
            type: LoadingType.Circular,
            color: 'primary'
        });

        this.routerSubscription = router.events.subscribe((event) => {
            if (event instanceof RouteConfigLoadStart) {
                this.loadingService.register('loadingLogin');
            }

            if (event instanceof RouteConfigLoadEnd) {
                this.loadingService.resolve('loadingLogin');
            }
        });

        this.userSubscription = authenticationService.authenticatedUserChanged.subscribe(usuario => this.usuario = usuario);
    }

    /**
     * 
     */
    ngOnInit() {
        this.getAuthenticatedUser();
    }

    /**
     * 
     */
    ngOnDestroy() {
        if (this.userSubscription) this.userSubscription.unsubscribe();
        if (this.routerSubscription) this.routerSubscription.unsubscribe();
    }

    /**
     *
     */
    public logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
        localStorage.clear();
    }

    /**
     *
     */
    public getAuthenticatedUser() {
        this.authenticationService.getAuthenticatedUser()
        .then(authenticatedUser => {
            if ( authenticatedUser ) {
                this.usuario = authenticatedUser;
            }
        });
    }

    /**
     * 
     */
    public openDialogChangePassword() {
        this.dialog.open(AlterarSenhaDialogComponent, {
            width: '360px',
            height: 'auto',
            data: { usuario: this.usuario || null }
        });
    }

    /**
     * Verifica se o usuário logado é ADMINISTRADOR e se está editando ele mesmo.
     */
    public itsMe( usuario: any ): boolean {
        const authenticatedUser = this.usuario;
        Promise.resolve(authenticatedUser);
        return (
            authenticatedUser &&
            authenticatedUser.isAdministrador &&
            authenticatedUser.id === usuario.id
        );
    }
}
