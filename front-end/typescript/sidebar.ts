import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('side-bar')
class SideBar extends LitElement{
    render() {
        return html`<div>Hello from another MyElement!<div>`;
    }
}

export default SideBar;