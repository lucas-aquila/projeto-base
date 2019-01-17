import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from '../../../../../services/usuario.service';
import { HomeViewComponent } from '../../../home-view.component';
import { MessageService } from '../../../../../services/message.service';

@Component({
    selector: 'detalhes-usuario',
    templateUrl: './detalhes-usuario.component.html',
    styleUrls: ['../usuario.component.scss']
})
export class DetalhesUsuarioComponent implements OnInit {

    /**
     * 
     */
    usuario: any = {};

    /**
     * 
     */
    itsMe: boolean;

    /**
     * 
     * @param activatedRoute 
     * @param router 
     * @param usuarioService 
     * @param homeView 
     * @param messageService 
     */
    constructor(
        public activatedRoute: ActivatedRoute,
        public router: Router,
        public usuarioService: UsuarioService,
        public homeView: HomeViewComponent,
        public messageService: MessageService
    ) {
        this.usuario.id = +this.activatedRoute.snapshot.params.id || null;
        homeView.toolbar.subhead = 'Usuário / Detalhes';
    }
    
    /**
     * 
     */
    ngOnInit() {
        if ( this.usuario && this.usuario.id ) {
            this.findById();
            this.itsMe = this.homeView.itsMe( this.usuario );
        } else {
            this.router.navigate(["/usuarios"]);
        }
    }

    /**
     * 
     */
    public findById() {
        this.usuarioService.findUsuarioById(this.usuario.id)
        .then((result) => {
            this.usuario = result;
        });
    }

    /**
     * 
     */
    public updateStatus() {
        this.usuarioService.updateStatusUsuario(this.usuario.id)
        .then((result) => {
            this.usuario.status = result.status;
            this.messageService.toastSuccess('Status alterado com sucesso.');
        });
    }
}
