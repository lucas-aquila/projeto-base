import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Http } from '@angular/http';
import { MatSnackBar } from '@angular/material';
import { HomeViewComponent } from '../../../home-view.component';
import { MessageService } from '../../../../../services/message.service';
import { SetorService } from '../../../../../services/setor.service';

@Component({
    selector: 'editar-setor',
    templateUrl: './editar-setor.component.html',
    styleUrls: ['../setor.component.scss']
})
export class EditarSetorComponent implements OnInit {
    
    /**
     * 
     */
    setor: any = {};

    /**
     * 
     * @param setorService 
     * @param router 
     * @param activatedRoute 
     * @param homeView 
     * @param messageService 
     */
    constructor(
        public setorService: SetorService,
        public router: Router,
        public activatedRoute: ActivatedRoute,
        public homeView: HomeViewComponent,
        public messageService: MessageService
    ) {
        if ( !this.activatedRoute.snapshot.params.id ) {
            homeView.toolbar.subhead = 'Setor / Adicionar';
        } else {
            homeView.toolbar.subhead = 'Setor / Editar';
            this.setor.id = +this.activatedRoute.snapshot.params.id;
        }
    }

    ngOnInit() {
        if ( this.setor && this.setor.id ) {
            this.findById();
        }
    }

    public findById() {
        this.setorService.findSetorById(this.setor.id)
        .then((result) => {
            this.setor = result;
        });
    }

    public insert( form ) {

        if ( form.invalid ) {
            this.messageService.toastWarning();
            return;
        }

        this.setorService.insertSetor(this.setor)
        .then((result) => {
            this.router.navigate(['/setores']);
            this.messageService.toastSuccess();
        });
    }

    public update( form ) {

        if ( form.invalid ) {
            this.messageService.toastWarning();
            return;
        }

        this.setorService.updateSetor(this.setor)
        .then((result) => {
            this.router.navigate(['/setores']);
            this.messageService.toastSuccess();
        });
    }
}
