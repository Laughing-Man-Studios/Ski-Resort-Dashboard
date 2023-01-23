import {LitElement, css, html, classMap} from 'https://cdn.jsdelivr.net/gh/lit/dist@2/all/lit-all.min.js';

export class Navbar extends LitElement {
  static properties = {
    _linksContainerClasses: { state: true },
    imageSrc: { type: String }
  };
  // Define scoped styles right with your component, in plain CSS
  static styles = css`
    #menu-button {
      margin: 0.5rem;
    }
    
    #menu-button div {
      width: 1.5rem;
      height: 0.2rem;
      background-color: white;
      margin: 0.3rem 0;
    }
    .hidden {
      display: none;
    }

    #nav-container {
      display: flex;
      justify-content: space-between;
      background-color: #4388cc;
    }
  `;

  constructor() {
    super();
    // Declare reactive properties
    this._linksContainerClasses = { hidden: true }
    this.imageSrc = '';
  }

  // Render the UI as a function of component state
  render() {
    return html`
      <div id="nav-container">
        <img src=${this.imageSrc} title="pageIcon"/>
        <div id="menu-button" >
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
      <div id="links-container" class=${classMap(this._linksContainerClasses)}>
          <slot name=""></slot>
        </div>
    `;
  }
}
customElements.define('nav-bar', Navbar);