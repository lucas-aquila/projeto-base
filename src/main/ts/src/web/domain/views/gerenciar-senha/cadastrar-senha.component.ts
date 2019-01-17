import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { MessageService } from '../../services/message.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, Validators, FormGroup, NgForm } from '@angular/forms';

@Component({
    selector: 'cadastrar-senha',
    templateUrl: 'cadastrar-senha.component.html',
    styleUrls: ['../login/login.component.scss']
})
export class CadastrarSenhaComponent implements OnInit {
    senha : FormControl;
    passForm: FormGroup;
    ngOnInit(): void {
        this.senha = new FormControl('', [
            Validators.required,
            Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&+,.])[A-Za-z\d$@$!%*#?&+,.]{8,}$/)
        ]);

        this.passForm = new FormGroup({
            'senha': this.senha
        }); 
    }
    /**
     * 
     */
    public usuario: any = {};

    /**
     * 
     * @param usuarioService 
     * @param messageService 
     * @param router 
     * @param activatedRoute 
     */
    constructor(
        public authService: AuthenticationService,
        public messageService: MessageService,
        public router: Router,
        public activatedRoute: ActivatedRoute
    ) {
        this.usuario.codigo = this.activatedRoute.snapshot.params.codigo || null;
    }

    /**
     * 
     * @param form 
     */
    public createPassword( form : NgForm ) {

        for(let inner in this.passForm.controls) {
            this.passForm.get(inner).markAsTouched();
        }
        //this.senha.markAsTouched();

        //this.senha.markAsTouched();
        if ( this.usuario.senha != this.usuario.confirmarSenha ) {
            this.messageService.toastWarning('As duas senhas não conferem.');
            return;
        }

        if ( this.passForm.invalid ) {
            this.messageService.toastWarning('Favor informar uma senha válida.');
            return;
        }

        this.authService.resetSenha( this.usuario.codigo, this.usuario.senha )
        .then(() => {
            this.messageService.toastSuccess('Senha cadastrada com sucesso.');
            this.router.navigate(['/login']);
        })
        .catch((ex) => {
            this.messageService.toastError('O prazo para cadastrar a senha foi expirado.')
            console.log(ex);
        }); 
    }
}
