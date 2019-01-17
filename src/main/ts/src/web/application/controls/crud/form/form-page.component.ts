import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CrudViewComponent } from '../crud-view.component';

@Component({
    selector: 'form-page',
    templateUrl: 'form-page.component.html'
})
export class FormPageComponent extends CrudViewComponent implements OnInit {

    // Armazena o link para voltar para a tela de consulta
    @Input() backLink: string;

    @Output() save = new EventEmitter();

    saveEntity = (form) => this.save.emit(form);

    /**
     * 
     */
    ngOnInit() {
        this.handleSubhead();
    }
    
    // Manipula o subtítulo da página de FORM
    handleSubhead = () => this.subhead = this.entity && this.entity.id ? `${this.subhead} / Editar` : `${this.subhead} / Adicionar`;
}
