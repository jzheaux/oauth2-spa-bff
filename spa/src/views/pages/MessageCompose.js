import Messages        from '../../services/Messages.js'
import Users        from '../../services/Users.js'

let MessageCompose = {

    render: async () => {
        const users = await Users.getUsers();
        let toUsers;
        if (users) {
            toUsers = `
                <select class="form-control" name="toUser" id="toUser">
                    <option value="">---Please select---</option>
                    ${ users.map(user => 
                        /*html*/`<option value="${user.userId}">${user.firstName} ${user.lastName}</option>`
                        ).join('\n ')
                    }
                </select>
            `;
        } else {
            toUsers = `
                <input name="toUser" id="toUser"/>
            `;
        }

        return /*html*/ `
            <div class="container">
                <h1>Compose</h1>
                <br />
                <form class="form-horizontal form-message-compose" action="#" th:action="@{/messages}" th:method="post">
                    <div class="form-group">
                        <label for="toUser" class="col-sm-1 control-label">To:</label>
                        <div class="col-sm-3">
                            ${toUsers}
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="subject" class="col-sm-1 control-label">Subject:</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="subject" name="subject">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="message" class="col-sm-1 control-label">Message:</label>
                        <div class="col-sm-7">
                            <textarea rows="3" class="form-control" id="message" name="message"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-3">
                            <button id="compose_submit_btn" type="submit" class="btn btn-primary">Send</button>
                        </div>
                    </div>
                </form>
            </div>
        `
    }
    // All the code related to DOM interactions and controls go in here.
    // This is a separate call as these can be registered only after the DOM has been painted
    , after_render: async () => {
        document.getElementById("compose_submit_btn").addEventListener ("click",  async () => {
            const message = {
                summary : document.getElementById("subject").value,
                text : document.getElementById("message").value,
                toId : document.getElementById("toUser").value
            };
            const response = await Messages.postMessage(message);
            console.log(response);
        })
    }
}

export default MessageCompose;