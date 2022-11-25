import './RecentUsersList.css';
import RecentUser from "../RecentUser/RecentUser";

export default function RecentUsersList({users, title='Recent Users'}) {

    const recentUsersView = users.map((user) => {
        return <div className={'col'} key={user.id}><RecentUser user ={user}/></div>;
    })

    return (<div>
        <section className="intro-single">
            <div className="container">
                <div className="row">
                    <div className="col-md-12 col-lg-8">
                        <div className="title-single-box">
                            <h1 className="title-single">{title}</h1>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section className="property-grid grid">
            <div className="container">
                <div className="row">
                    {recentUsersView}
                </div>
            </div>
        </section>

    </div>);
}


