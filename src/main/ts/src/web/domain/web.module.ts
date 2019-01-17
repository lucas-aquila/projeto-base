// ANGULAR
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClient } from '@angular/common/http';

// MODULES
import { SharedModule } from '../../shared/shared.module';
import { WebRoutingModule } from './web.routing.module';

// UTILS
import { Interceptor } from '../application/interceptor/interceptor';
import { Describer } from '../application/describer/describer';
import { AuthGuard } from './services/auth-guard.service';
import { FocusOnInitDirective } from './utils/focus-on-init.directive';


// SERVICES
import { AuthenticationService } from './services/authentication.service';
import { UsuarioService } from './services/usuario.service';
import { WildcardService } from './services/wildcard.service';
import { MessageService } from './services/message.service';
import { DialogService } from './services/dialog.service';


// PIPES
import { CapitalizePipe } from './pipes/capitalize.pipe';
import { UserInitialsPipe } from './pipes/user-initials.pipe';
import { LOCALE_ID } from '@angular/core';

// COMPONENTS
import { WebComponent } from './views/web.component';
import { LoginComponent } from './views/login/login.component';
import { HomeViewComponent } from './views/home/home-view.component';
import { RecuperarSenhaComponent } from './views/gerenciar-senha/recuperar-senha.component';
import { CadastrarSenhaComponent } from './views/gerenciar-senha/cadastrar-senha.component';

// CONTROLS
import { HorizontalSpaceComponent } from '../application/controls/horizontal-space.component';
import { VerticalSpaceComponent } from '../application/controls/vertical-space.component';
import { DeleteDialogComponent } from '../application/controls/delete-dialog/delete-dialog.component';
import { CrudViewComponent } from '../application/controls/crud/crud-view.component';
import { ListPageComponent } from '../application/controls/crud/list/list-page.component';
import { DetailPageComponent } from '../application/controls/crud/detail/detail-page.component';
import { FormPageComponent } from '../application/controls/crud/form/form-page.component';
import { NoRecordsFoundComponent } from '../application/controls/no-records-found/no-records-found.component';

// =============================================
// CADASTROS
// =============================================
// USUARIOS
import { ConsultarUsuariosComponent } from './views/home/cadastros/usuario/consultar/consultar-usuarios.component';
import { DetalhesUsuarioComponent } from './views/home/cadastros/usuario/detalhes/detalhes-usuario.component';
import { EditarUsuarioComponent } from './views/home/cadastros/usuario/editar/editar-usuario.component';
import { AlterarSenhaDialogComponent } from './views/home/cadastros/usuario/alterar-senha-dialog.component';



//SETORES
import { ConsultarSetoresComponent } from './views/home/cadastros/crudexemplo-setor/consultar/consultar-setores.component';
import { DetalhesSetorComponent } from './views/home/cadastros/crudexemplo-setor/detalhes/detalhes-setor.component';
import { EditarSetorComponent } from './views/home/cadastros/crudexemplo-setor/editar/editar-setor.component';



// INTERNACIONALIZACAO MATERIAL PAGINATOR
import { getPaginatorIntl } from './services/portuguese-paginator-intl';
import { MatPaginatorIntl } from '@angular/material';

import localePt from '@angular/common/locales/pt';
registerLocaleData(localePt, 'pt-BR');

/**
 * The internationalization (i18n) library for Angular 2+
 * https://github.com/ngx-translate/core
 */
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { PaginationService } from './services/pagination.service';

// Custom TranslateLoader while using AoT compilation
export function customTranslateLoader( http: HttpClient ) {
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

/**
 *
 */
@NgModule({
    declarations: [
        // COMPONENTS
        WebComponent,
        LoginComponent,
        HomeViewComponent,
        RecuperarSenhaComponent,
        CadastrarSenhaComponent,

        // CONTROLS
        HorizontalSpaceComponent,
        VerticalSpaceComponent,
        DeleteDialogComponent,
        CrudViewComponent,
        ListPageComponent,
        DetailPageComponent,
        FormPageComponent,
        NoRecordsFoundComponent,

        // Usuario
        ConsultarUsuariosComponent,
        DetalhesUsuarioComponent,
        EditarUsuarioComponent,
        AlterarSenhaDialogComponent,
        // Setor
        ConsultarSetoresComponent,
        DetalhesSetorComponent,
        EditarSetorComponent,

        // PIPES
        CapitalizePipe,
        UserInitialsPipe,

        // DIRECTIVES
        FocusOnInitDirective,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        SharedModule,
        WebRoutingModule,
        HttpClientModule,

        // Translate i18n
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (customTranslateLoader),
                deps: [HttpClient]
            }
        })
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [],
    entryComponents: [
        AlterarSenhaDialogComponent,
        DeleteDialogComponent,
    ],
    providers: [
        // Services
        Describer,
        WildcardService,
        PaginationService,
        UsuarioService,
        AuthenticationService,
        MessageService,
        DialogService,

        AuthGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: Interceptor,
            multi: true
        },

        // Internacionalizacao MatPaginator
        { provide: MatPaginatorIntl, useValue: getPaginatorIntl() },
        { provide: LOCALE_ID, useValue: 'pt-BR' }
    ],
    bootstrap: [WebComponent]
})
export class WebModule {}
