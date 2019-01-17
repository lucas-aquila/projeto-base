import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsuarioService } from '../../../../services/usuario.service';
import { MessageService } from '../../../../services/message.service';
import { FormControl, FormGroup, Validators } from "@angular/forms"

@Component({
    selector: 'alterar-senha-dialog',
    templateUrl: 'alterar-senha-dialog.component.html',
    styleUrls: ['./alterar-senha-dialog.component.scss']
})
export class AlterarSenhaDialogComponent implements OnInit {

    /**
     * 
     */
    public usuarioDialog: any = {
        senhaAtual: '',
        novaSenha: '',
        confirmarNovaSenha: ''
    };

    novaSenhaTouched = false;
    passForm: FormGroup;
    novaSenha: FormControl;

    /**
     * 
     */
    ngOnInit(){
        this.novaSenha = new FormControl(this.usuarioDialog.novaSenha, [
            Validators.required,
            Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&+,.])[A-Za-z\d$@$!%*#?&+,.]{8,}$/)
        ]);

        this.passForm = new FormGroup({
            'novaSenha': this.novaSenha
        });       
    }

    /**
     * 
     * @param dialogRef 
     * @param data 
     * @param usuarioService 
     * @param messageService 
     */
    constructor(
        public dialogRef: MatDialogRef<AlterarSenhaDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        public usuarioService: UsuarioService,
        public messageService: MessageService
    ) {}

    /**
     * 
     * @param form 
     */
    public updateSenhaUsuario( form ) {

        for(let inner in this.passForm.controls) {
            this.passForm.get(inner).markAsTouched();
        }

        if ( this.passForm.invalid ) {
            this.messageService.toastWarning();
            return;
        }

        const { id } = this.data.usuario || null;
        const { senhaAtual, novaSenha } = this.usuarioDialog;

        this.usuarioService.updateSenhaUsuario(id, senhaAtual, novaSenha)
        .then(() => {
            this.dialogRef.close();
            this.messageService.toastSuccess('Senha atualizada com sucesso.');
        }); 
    }

    markAsTouched() {
        this.novaSenhaTouched = true;
    }
}
