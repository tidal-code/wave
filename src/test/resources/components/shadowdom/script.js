        const el = document.createElement('div');
        el.setAttribute("class", "firstshadow");

        const wrapper = document.createElement("span");


        const shadowRoot1 = el.attachShadow({mode: 'open'})
        shadowRoot1.innerHTML = "<span class='content'> <div class='target1 button'>Shadow Element 1-1</div> <div class='target2 button'>Shadow Element 1-2</div> </span>";
        
        const container = document.querySelector('body');
        container.appendChild(el);
        container.appendChild(wrapper);
        
        const e2 = document.createElement('div');
        e2.setAttribute("class", "secondshadow");
        const shadowRoot2 = e2.attachShadow({mode: 'open'})
        shadowRoot2.innerHTML = "<span class='content'> <div class='target1 button'>Shadow Element 2-1</div> <div class='target3 button'>Shadow Element 2-2</div> </span>";
        container.appendChild(e2);
