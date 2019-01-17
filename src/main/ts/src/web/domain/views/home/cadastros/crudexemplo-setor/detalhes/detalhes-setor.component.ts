import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HomeViewComponent } from '../../../home-view.component';
import { MessageService } from '../../../../../services/message.service';
import { SetorService } from '../../../../../services/setor.service';

@Component({
    selector: 'detalhes-setor',
    templateUrl: './detalhes-setor.component.html',
    styleUrls: ['../setor.component.scss']
})
export class DetalhesSetorComponent implements OnInit {

    /**
     * 
     */
    setor: any = {};

    /**
     * 
     * @param activatedRoute 
     * @param router 
     * @param tipologiaService 
     * @param homeView 
     * @param messageService 
     */
    constructor(
        public activatedRoute: ActivatedRoute,
        public router: Router,
        public setorService: SetorService,
        public homeView: HomeViewComponent,
        public messageService: MessageService
    ) {
        this.setor.id = +this.activatedRoute.snapshot.params.id || null;
        homeView.toolbar.subhead = 'Setor / Detalhes';
    }

    /**
     * 
     */
    ngOnInit() {
        if ( this.setor && this.setor.id ) {
            this.findById();
        } else {
            this.router.navigate(["/setores"]);
        }
    }

    /**
     * 
     */
    public findById() {
        this.setorService.findSetorById(this.setor.id)
        .then((result) => {
            this.setor = result;
        });
    }

    /**
     * 
     */
    public updateStatus() {
        this.setorService.updateStatusSetor(this.setor.id)
        .then((result) => {
            this.setor.status = result.status;
            this.messageService.toastSuccess('Status alterado com sucesso.');
        });
    }
}
