package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="supervisor_activity_template_published",
		label="Model d'activité publié",	
		comodel=ActivityTemplate.class
	)
public interface ActivityTemplatePublished extends ActivityTemplate {
	
	@Field(
		label="Profil",
		rel= Relation.MANY2ONE
	)
	Profile profile() throws IOException;
	
	@Field(label="Icon")
	String icon() throws IOException;
	
	@Field(label="Nb Vues")
	Long nbViews() throws IOException;
	
	@Field(label="Nb Likes")
	Long nbLikes() throws IOException;
	
	@Field(label="Nb Paid")
	Long nbPaid() throws IOException;
	
	@Field(label="Nb Abonnements")
	Long nbSubscriptions() throws IOException;
	
	@Field(label="Score")
	double score() throws IOException;
	
	@Field(label="Prix")
	double price() throws IOException;
	
	@Field(label="Disponibilité")
	TemplateAvailability availability() throws IOException;
	
	CommentsScored advices() throws IOException;
	
	void defineIcon(String icon) throws IOException;
	void changeLicense(TemplateLicense license) throws IOException;	
	void changePrice(double amount) throws IOException;
	void changeProfile(Profile profile) throws IOException;
	
	void makeObsolete() throws IOException;
	void makeAvailable() throws IOException;
	
	boolean likedBy(User user) throws IOException;
	boolean viewedBy(User user) throws IOException;
	
	void like() throws IOException;
	void view() throws IOException;
	void pay() throws IOException;
	void subscribe() throws IOException;
}
