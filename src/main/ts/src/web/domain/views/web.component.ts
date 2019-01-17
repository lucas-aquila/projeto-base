import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'web-root',
    template: `<router-outlet></router-outlet>`
})
export class WebComponent implements OnInit {
    /**
     * Usado para fazer scroll até o topo da página
     */
    @ViewChild('top') topElement: any;
    currentUrl: any;

    /**
     * 
     * @param router 
     */
    constructor(public router: Router) {}

    /**
     *
     */
    ngOnInit() {
        this.scrollToTop();    
    }
    
    /**
     * Sempre que trocar a rota,
     * faz o scroll para o inicio do scroll de todos os sidenav-containers
     * que é uma div criada pelo componente side-nav
     */
    private scrollToTop() {
        this.router.events.subscribe((path: any) => {
            if (path.url != this.currentUrl) {
                let scrollableElements = document.getElementsByClassName('mat-drawer-content');
                
                for (let i = 0; i < scrollableElements.length; i++) {
                    scrollableElements[i].scrollTop = 0;
                }
                
                this.currentUrl = path.url;
            }
        });
    }
}
