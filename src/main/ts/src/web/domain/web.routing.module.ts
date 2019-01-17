import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// COMPONENTS
import { AuthGuard } from './services/auth-guard.service';
import { LoginComponent } from './views/login/login.component';
import { HomeViewComponent } from './views/home/home-view.component';
import { CadastrarSenhaComponent } from './views/gerenciar-senha/cadastrar-senha.component';
import { RecuperarSenhaComponent } from './views/gerenciar-senha/recuperar-senha.component';

// =============================================
// CADASTROS
// =============================================
// USUARIOS
import { ConsultarUsuariosComponent } from './views/home/cadastros/usuario/consultar/consultar-usuarios.component';
import { EditarUsuarioComponent } from './views/home/cadastros/usuario/editar/editar-usuario.component';
import { DetalhesUsuarioComponent } from './views/home/cadastros/usuario/detalhes/detalhes-usuario.component';

// SETORES
import { ConsultarSetoresComponent } from './views/home/cadastros/crudexemplo-setor/consultar/consultar-setores.component';
import { EditarSetorComponent } from './views/home/cadastros/crudexemplo-setor/editar/editar-setor.component';
import { DetalhesSetorComponent } from './views/home/cadastros/crudexemplo-setor/detalhes/detalhes-setor.component';


const routes: Routes = [
    { path: '', redirectTo: '/usuarios', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'recuperar-senha', component: RecuperarSenhaComponent },
    { path: 'cadastrar-senha/:codigo', component: CadastrarSenhaComponent },
    {
        path: '',
        component: HomeViewComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: 'setores',
                children: [
                    { path: '', component: ConsultarSetoresComponent },
                    { path: 'adicionar', component: EditarSetorComponent },
                    { path: 'editar/:id', component: EditarSetorComponent },
                    { path: 'detalhes/:id', component: DetalhesSetorComponent }
                ]
            },
            {
                path: 'usuarios',
                children: [
                    { path: '', component: ConsultarUsuariosComponent },
                    { path: 'adicionar', component: EditarUsuarioComponent },
                    { path: 'editar/:id', component: EditarUsuarioComponent },
                    { path: 'detalhes/:id', component: DetalhesUsuarioComponent }
                ]
            },
        ]
    }
];

/**
 *
 */
@NgModule({
    imports: [RouterModule.forRoot(routes, { useHash: true })],
    exports: [RouterModule],
    providers: []
})
export class WebRoutingModule {}
