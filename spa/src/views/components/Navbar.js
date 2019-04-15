import Users        from './../../services/Users.js'

let Navbar = {
    render: async () => {
        const currentUser = await Users.getCurrentUser();
        let account;
        if ( currentUser ) {
            account = /*html*/`
                <a id="user-menu" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${currentUser.firstName} ${currentUser.lastName}</a>
                <ul class="dropdown-menu">
                    <li><a id="sign-out" href='javascript:document.logoutForm.submit()'>Sign Out</a></li>
                </ul>
                <form name="logoutForm" action="http://localhost:8080/logout" method="post">
                    <input hidden type="submit" value="Sign Out"/>
                </form>
            `
        } else {
            account = /*html*/`
                <a href='http://localhost:8080/login'>Login</a>
            `
        }
        let view =  /*html*/`
            <div>
                <nav class="navbar navbar-default">
                    <div class="container">
                        <div class="container-fluid">
                            <div class="navbar-header">
                                <button aria-controls="navbar" aria-expanded="false" class="navbar-toggle collapsed" data-target="#navbar" data-toggle="collapse" type="button">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                    <span class="icon-bar"></span>
                                </button>
                                <a class="navbar-brand" href="#"><img src="/assets/img/logo.png"/></a>
                            </div>
                            <div class="navbar-collapse collapse" id="navbar">
                                <ul class="nav navbar-nav">
                                    <li><a href="#/inbox/inbox">Inbox</a></li>
                                    <li><a href="#/inbox/sent">Sent</a></li>
                                    <li><a href="#/compose">Compose</a></li>
                                </ul>
                                <ul class="nav navbar-nav navbar-right">
                                    <li class="dropdown">
                                        ${account}
                                    </li>
                                </ul>
                            </div><!--/.nav-collapse -->
                        </div><!--/.container-fluid -->
                    </div>
                </nav>
            </div>
        `
        return view
    },
    after_render: async () => { }

}

export default Navbar;