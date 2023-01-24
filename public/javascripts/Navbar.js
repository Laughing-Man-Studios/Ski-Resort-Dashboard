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
      background-color: var(--white, white);
      margin: 0.3rem 0;
    }
    .hidden {
      display: none;
    }

    #nav-container {
      display: flex;
      justify-content: space-between;
      background-color: var(--blue, blue);
    }

    #nav-container img {
      height: 1.8rem;
      background-color: var(--white, white);
      margin: 0.5rem;
    }

    ::slotted(a) {
      display: flex;
      align-items: center;  
      background-color: var(--blue, blue);
      color: white;
      border-top: solid 0.1rem var(--green, green);
    }

    @media only screen and (min-width: 769px) {
        .hidden {
          display: block;
        }

        #nav-container {
          display: none;
        }

        #links-container {
          padding-top: 1rem;
        }

        ::slotted(a) {
          padding: 0.25rem 1rem;
          text-align: center;
          border-top: initial;
          border-right: solid 0.15rem var(--green, green)
        }

        ::slotted(.selected) {
          border: solid 0.15rem var(--green, green);
        }
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
        <div id="menu-button" @click=${this._toggleMenu}>
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
      <div id="links-container" class=${classMap(this._linksContainerClasses)}>
          <slot @slotchange=${this._handleSlotChange}></slot>
        </div>
    `;
  }

  _toggleMenu() {
    this._linksContainerClasses = {
      ...this._linksContainerClasses,
      hidden: !this._linksContainerClasses.hidden
    }
  }

  _handleSlotChange(e) {
    const childNodes = e.target.assignedNodes();
    childNodes.forEach(element => {
      if (element.querySelector) {
        const img = element.querySelector('img');
        if (img) {
          img.style.height = '1.8rem';
          img.style.backgroundColor = css`var(--white, white)`;
          img.style.margin = '0.5rem';
        }
        const p = element.querySelector('p');
        if (p) {
          p.style.margin = '0.5rem auto';
        }
      }
    });
  }
}
customElements.define('nav-bar', Navbar);