import {LitElement, css, html} from 'https://cdn.jsdelivr.net/gh/lit/dist@2/core/lit-core.min.js';

export class Navbar extends LitElement {
  static properties = {
    name: {},
  };
  // Define scoped styles right with your component, in plain CSS
  static styles = css`
    :host {
      color: blue;
    }
  `;

  constructor() {
    super();
    // Declare reactive properties
    this.isOpen = false;
  }

  // Render the UI as a function of component state
  render() {
    return html`
      <div>
        <slot name=""></slot>
      </div>
    `;
  }
}
customElements.define('nav-bar', Navbar);