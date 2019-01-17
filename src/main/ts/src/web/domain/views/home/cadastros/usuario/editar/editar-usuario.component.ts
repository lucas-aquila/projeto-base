import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../../../../services/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { HomeViewComponent } from '../../../home-view.component';
import { MessageService } from '../../../../../services/message.service';
import { debounce } from "../../../../../utils/debounce";
import { FormControl, FormGroup } from "@angular/forms"

@Component({
    selector: 'editar-usuario',
    templateUrl: './editar-usuario.component.html',
    styleUrls: ['../usuario.component.scss']
})
export class EditarUsuarioComponent implements OnInit {
    
    /**
     * 
     */
    usuario: any = {
        organizacao: {},
        perfis: []
    };

    /**
     * 
     */
    itsMe: boolean;
    error: boolean;

    /**
     * 
     */
    perfis: any = [];

    /**
     * Utilizado para o autocomplete
     */
    organizacoes: any = [];
    organizacaoNome: string = '';

    /**
     * Exibir senha
     */
    inputType: string = 'password';


    checkboxGroup: FormGroup = new FormGroup({});

    public debounce = debounce;

    /**
     * 
     * @param usuarioService 
     * @param router 
     * @param activatedRoute 
     * @param homeView 
     * @param messageService 
     * @param organizacaoService 
     */
    constructor(
        public usuarioService: UsuarioService,
        public router: Router,
        public activatedRoute: ActivatedRoute,
        public homeView: HomeViewComponent,
        public messageService: MessageService,
    ) {
        if ( !this.activatedRoute.snapshot.params.id ) {
            homeView.toolbar.subhead = 'Usuário / Adicionar';
        } else {
            homeView.toolbar.subhead = 'Usuário / Editar';
            this.usuario.id = +this.activatedRoute.snapshot.params.id;
        }
    }

    ngOnInit() {
        if ( this.usuario && this.usuario.id ) {
            this.findById();
            this.itsMe = this.homeView.itsMe( this.usuario );
        }
        
        this.listPerfis();
    }

    public findById() {
        this.usuarioService.findUsuarioById(this.usuario.id)
        .then((result) => {
            this.usuario = result;
        });
    }

    public insert( form ) {

        if ( form.invalid ) {
            this.messageService.toastWarning();
            return;
        }

        if (this.isString(this.usuario.organizacao)){
            this.messageService.toastWarning("Nenhuma organização válida foi selecionada.");
            return;
        }

        this.usuarioService.insertUsuario(this.usuario)
        .then((result) => {
            this.router.navigate(['/usuarios']);
            this.messageService.toastSuccess(`Novo usuário cadastrado. Instruções de acesso foram enviadas para o seu e-mail.`, 5);
        });
    }

    public update( form ) {

        if ( form.invalid ) {
            this.messageService.toastWarning();
            return;
        }

        if (this.isString(this.usuario.organizacao)){
            this.messageService.toastWarning("Nenhuma organização válida foi selecionada.");
            return;
        }

        this.usuarioService.updateUsuario(this.usuario)
        .then((result) => {
            this.router.navigate(['/usuarios']);
            this.messageService.toastSuccess();
        });
    }

    public listPerfis() {
        this.usuarioService.listEnumPerfis()
        .then((result) => {
            this.perfis = result;
            /**
             * Adiciona para cada elemento do array um fromControl de mesmo nome checado ou nao
             * toma como base a lista de todos os perfis e seleciona só os que o usuario atual tem
             */
            // result.forEach(value => {
            //     this.checkboxGroup.addControl(value, new FormControl(this.usuario.perfis.indexOf(value) !== -1));
            // });
        });
    }

    private getSelectedPerfis(){
        let newPerfis = this.checkboxGroup.value;//pega um objeto com {PERFIL: true || false}
        return Object.keys(newPerfis).filter(perfil => newPerfis[perfil]==true)//cria um array baseado no objeto de cima mas só com selected true
    }

    /**
     * Exibe organizações para o autocomplete
     */
    public listOrganizacoes() {
        let nomeOrganizacao = this.usuario.organizacao || null;
        if ( this.isString(nomeOrganizacao) && !nomeOrganizacao.length ) {
            this.organizacoes = [];
            return;
        }

        const organizacoesAtivas = true;

        /*this.organizacaoService.listOrganizacoesByFilters(nomeOrganizacao, organizacoesAtivas)
        .then((result) => {
            this.organizacoes = result.content;
        });*/
    }

    /**
     * Expressão específica para autocomplete
     */
    public displayNomeOrganizacao( organizacao ) {
        return organizacao && organizacao.nome ? organizacao.nome : null;
    }

    /**
     * Exibir senha
     */
    public showPassword() {
        this.inputType = this.inputType === 'password' ? 'text' : 'password';
    }

    /**
     * Helper
     * @param value 
     */
    public isString( value ): boolean {
        return typeof value === 'string';
    }

    /**
     * 
     */
    public normalizeOrganizacao() {
        if (this.isString(this.usuario.organizacao)){
            this.error=false;
            this.usuario.organizacao=null;
        }
    }
}
