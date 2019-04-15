let Bottombar = {
    render: async () => {
        let view =  /*html*/`
        <div>
            <footer>
                <div class="container hidden-print">
                    <div class="text-center">
                        <p class="muted credit">Visit the <a href="https://spring.io/projects/spring-security" target="_blank">Spring Security</a> site for more <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#sample-apps" target="_blank">samples</a>.</p>
                    </div>
                </div>
            </footer>
        </div>
        `
        return view
    },
    after_render: async () => { }

}

export default Bottombar;