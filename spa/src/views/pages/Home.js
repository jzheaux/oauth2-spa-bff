import Messages from '../../services/Messages.js'
import Utils from '../../services/Utils.js'

// --------------------------------
//  Define Data Sources
// --------------------------------

let Home = {
    render : async () => {
        let request = Utils.parseRequestURL()
        let type = request.id || 'inbox';
        let messages = await Messages.getMessages(type);

        let view;

        if (messages) {
            // add sent messages
            view =  /*html*/`
                <div class="container">
                    <h1>${type}</h1>
                    <br />
                    <div>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th class="col-sm-2">Created</th>
                                    <th class="col-sm-2">To</th>
                                    <th class="col-sm-2">From</th>
                                    <th class="col-sm-5">Summary</th>
                                    <th class="col-sm-1">Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${ messages.map(message => 
                                    /*html*/`
                                    <tr>
                                        <td class="col-sm-2">${message.created}</td>
                                        <td class="col-sm-2">${message.toId}</td>
                                        <td class="col-sm-2">${message.fromId}</td>
                                        <td class="col-sm-5">
                                            <a href="#/messages/${message.id}">${message.summary}</a>
                                        </td>
                                        <td class="col-sm-1">
                                            <div>
                                                <button id="message-delete-${message.id}" class="btn btn-default btn-sm">Delete</button>
                                            </div>
                                        </td>
                                    </tr>
                                    `
                                    ).join('\n ')
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            `
        } else {
            view = /*html*/`
                <div class="container">No messages</div>
            `
        }

        return view
    }
    , after_render: async () => {
        // add delete
    }

}

export default Home;